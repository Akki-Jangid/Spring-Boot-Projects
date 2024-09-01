package com.Banking.ServiceImpl;

import com.Banking.Configuration.TwilioConfig;
import com.Banking.Dto.Response.TransactionResponseDto;
import com.Banking.Dto.Sms.SmsRequest;
import com.Banking.Dto.Request.TransactionDto;
import com.Banking.Exception.Classes.ResourceNotFoundException;
import com.Banking.Model.Account.Account;
import com.Banking.Model.AppUser.AppUser;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.Model.Transaction.Transaction;
import com.Banking.Repository.TransactionRepository;
import com.Banking.Service.TransactionService;
import com.Banking.Util.AccountUtil;
import com.Banking.Util.JwtTokenUtil;
import com.Banking.Util.TransactionUtil;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionUtil transactionUtil;

    @Autowired
    private AccountUtil accountUtil;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private TwilioConfig twilioConfig;

    @Override
    public ApiResponse createTransaction(TransactionDto transactionDto) {
        Transaction transaction = validateAndAdjustDetailsAfterTransaction(transactionDto);
        transaction.setTimeStamp(new Date());
        transactionRepository.save(transaction);
        return new ApiResponse(true, "Transaction Details saved Successfully");
    }

    private Transaction validateAndAdjustDetailsAfterTransaction(TransactionDto transactionDto) {
        AppUser user =  tokenUtil.getAppUserFromToken();
        Account senderAccount = accountUtil.findByUserId(user.getId());
        Account receiverAccount = accountUtil.findById(transactionDto.getReceiverAccountId());

        if((senderAccount.getBalance().compareTo(transactionDto.getAmount())<0))
            throw new ResourceNotFoundException("Unable to Complete Transaction: Insufficient Account Balance");
        BigDecimal remainingBalance = senderAccount.getBalance().subtract(transactionDto.getAmount());
        senderAccount.setBalance(remainingBalance);
        BigDecimal receiverBalance = receiverAccount.getBalance().add(transactionDto.getAmount());
        receiverAccount.setBalance(receiverBalance);

        sendTransactionNotification(transactionDto.getAmount(), senderAccount, receiverAccount);
        return new Transaction(transactionDto, senderAccount, receiverAccount);
    }

    private void sendTransactionNotification(BigDecimal amount, Account senderAccount, Account receiverAccount) {
        String receiverMessage = "INR "+amount+" has been credited to your" +
                " account from Account No. "+senderAccount.getAccountNumber()+". Current balance: " +
                "INR "+receiverAccount.getBalance()+".";
        String senderMessage = "INR "+amount+" Debited from your account to Account" +
                " No. "+receiverAccount.getAccountNumber()+
                ". Remaining balance: INR "+senderAccount.getBalance()+".";
        SmsRequest senderSms = new SmsRequest("+91"+senderAccount.getUser().getPhoneNumber(), senderMessage);
        SmsRequest receiverSms = new SmsRequest("+91"+receiverAccount.getUser().getPhoneNumber(), receiverMessage);
        sendSms(senderSms);
        sendSms(receiverSms);
    }

    private void sendSms(SmsRequest sms) {
        try{
            Message message = Message.creator(
                    new PhoneNumber(sms.getDestinationPhoneNumber()),
                    new PhoneNumber(twilioConfig.getTwilioPhoneNumber()),
                    sms.getSmsBody()
            ).create();
            log.info("Notification has been sent to the User with " +
                    "SID : "+message.getAccountSid());
        } catch (Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public TransactionResponseDto getTransactionById(Long id) {
        return new TransactionResponseDto(transactionUtil.findById(id));
    }

    @Override
    public Page<TransactionResponseDto> getAllTransaction(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Transaction> transactionPage = transactionRepository.findAll(pageable);
        List<TransactionResponseDto> transactionDtoList = transactionPage.stream().map(TransactionResponseDto::new).toList();
        return new PageImpl<>(transactionDtoList, pageable, transactionPage.getTotalElements());
    }

    @Override
    public ApiResponse updateTransactionById(Long id, TransactionDto transactionDto) {
        Transaction transaction = transactionUtil.findById(id);
        updateTransactionDetails(transaction, transactionDto);
        transactionRepository.save(transaction);
        return new ApiResponse(true, "Transaction Details updated Successfully");
    }

    private void updateTransactionDetails(Transaction transaction, TransactionDto transactionDto) {
//        if(senderAccount!=null) {
//            Account senderAccount = accountUtil.findById(transactionDto.getSenderAccountId());
//            transaction.setSenderAccount(senderAccount);
//        }
        if(transactionDto.getReceiverAccountId()!=null) {
            Account receiverAccount = accountUtil.findById(transactionDto.getReceiverAccountId());
            transaction.setReceiverAccount(receiverAccount);
        }
        if(transactionDto.getAmount()!=null) {
            Transaction transaction1 = validateAndAdjustDetailsAfterTransaction(transactionDto);
            transaction.setAmount(transaction1.getAmount());
        }
        if(!transactionDto.getDescription().isEmpty()) transaction.setDescription(transaction.getDescription());
    }

    @Override
    public ApiResponse deleteTransactionById(Long id) {
        transactionUtil.deleteByTransactionById(id);
        return new ApiResponse(true, "Transaction Deleted Successfully");
    }
}
