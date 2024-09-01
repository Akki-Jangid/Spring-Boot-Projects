package com.Irctc.ServiceImpl;

import com.Irctc.Configuration.TwilioConfig;
import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.BookingDto.BookingDto;
import com.Irctc.Dto.BookingDto.BookingRequestDto;
import com.Irctc.Dto.TrainDto.TrainRouteDto;
import com.Irctc.Dto.TwilioDto.SmsRequest;
import com.Irctc.Dto.UserDto.AppUserDto;
import com.Irctc.ExceptionHandler.ResourceNotFoundException;
import com.Irctc.JwtSecurity.JwtHelper;
import com.Irctc.JwtSecurity.JwtTokenDetails;
import com.Irctc.Model.Booking;
import com.Irctc.Model.TrainModels.TrainRoute;
import com.Irctc.Model.User.AppUser;
import com.Irctc.Repository.BookingRepository;
import com.Irctc.Service.AppUserService;
import com.Irctc.Service.BookingService;
import com.Irctc.Service.TrainRouteService;
import com.Irctc.Service.TrainService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private TrainRouteService trainRouteService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private JwtTokenDetails tokenDetails;

    @Autowired
    private TwilioConfig twilioConfig;

    public Booking findBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with ID " + id + " not found"));
    }

    @Override
    public ApiResponse createBooking(BookingRequestDto bookingRequestDto) {
        AppUser appUser =  tokenDetails.getAppUserFromToken();
        TrainRoute trainRoute = trainRouteService.findTrainRouteById(bookingRequestDto.getTrainRouteId());
        checkForSeatBooking(bookingRequestDto, appUser, trainRoute);

        String body = "Welcome "+ appUser.getName()+" to IRCTC\nYour "+bookingRequestDto.getSeatsBooked()+" seats booked successfully" +
                " for the train "+trainRoute.getTrain().getTrainName()+" ("+trainRoute.getTrain().getTrainNumber()+") " +
                "on "+trainRoute.getJourneyDate()+" from "+trainRoute.getStartingPoint()+" to "+trainRoute.getDestination()+"\n" +
                "THANKS FOR USING IRCTC\nHAPPY JOURNEY...";
        messageNotification(body);


        return new ApiResponse(true, "Ticket Booked Successfully");
    }

    private void messageNotification(String body) {

        try{
            SmsRequest request = new SmsRequest("+917678177165", body);
            Message.creator(new PhoneNumber(request.getDestinationPhoneNumber()),
                    new PhoneNumber(twilioConfig.getTwilioPhoneNumber()),
                    request.getSmsBody()).create();
        } catch(Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    private void checkForSeatBooking(BookingRequestDto bookingRequestDto, AppUser appUser, TrainRoute trainRoute) {

        int remainingSeats = trainRoute.getAvailableSeats() - bookingRequestDto.getSeatsBooked();
        if (remainingSeats < 0) throw new ResourceNotFoundException("No Seats Available");
        if(bookingRequestDto.getSeatsBooked()<1) throw new ResourceNotFoundException("Booking Seats must be greater than 1");

        if (bookingRequestDto.getSeatsBooked() <= trainRoute.getAvailableSeats()) {
                trainRoute.setAvailableSeats(remainingSeats);
                Booking booking = new Booking(bookingRequestDto, trainRoute, appUser);
                booking.setBookingDate(new Date());
                bookingRepository.save(booking);
        } else throw new ResourceNotFoundException("Only " + trainRoute.getAvailableSeats() + " seats are available");
    }

    @Override
    public BookingDto getBookingById(long id) {
        Booking booking = findBookingById(id);
        TrainRouteDto trainRouteDto = new TrainRouteDto(booking.getTrainRoute());
        AppUserDto appUserDto = new AppUserDto(booking.getAppUser());
        return new BookingDto(booking);
    }

    @Override
    public Page<BookingDto> getAllBooking(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Booking> bookingPage = bookingRepository.findAll(pageable);
        List<BookingDto> bookingDtoList = bookingPage.stream().map(BookingDto::new).toList();

        return new PageImpl<>(bookingDtoList, pageable, bookingPage.getTotalElements());
    }

    @Override
    public Page<BookingDto> getAllBookingByTrain(long trainId, int pageNumber, int pageSize) {
        trainService.findTrainById(trainId);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Booking> bookingDtoPage = bookingRepository.findByTrain(trainId, pageable);

        List<BookingDto> bookingDtoList = bookingDtoPage.stream().
                map(BookingDto::new).toList();

        return new PageImpl<>(bookingDtoList, pageable, bookingDtoPage.getTotalElements());
    }

    @Override
    public ApiResponse updateBooking(Long id, BookingRequestDto bookingRequestDto) {
        Booking booking = findBookingById(id);

        if (bookingRequestDto.getSeatsBooked() != 0) {
            int availableSeats = booking.getTrainRoute().getAvailableSeats();
            int remainingSeats = booking.getSeatsBooked() - bookingRequestDto.getSeatsBooked();
            booking.getTrainRoute().setAvailableSeats(availableSeats + remainingSeats);
            booking.setSeatsBooked(bookingRequestDto.getSeatsBooked());
        }

        bookingRepository.save(booking);
        return new ApiResponse(true, "Booking Details Updated Successfully");
    }

    @Override
    public ApiResponse deleteBookingById(long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking is not found with id : " + id));
        int availableSeat = booking.getTrainRoute().getAvailableSeats();
        booking.getTrainRoute().setAvailableSeats(availableSeat + booking.getTrainRoute().getAvailableSeats());
        bookingRepository.delete(booking);
        return new ApiResponse(true, "Booking Deleted Successfully");
    }
}
