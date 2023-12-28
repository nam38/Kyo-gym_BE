package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.config.VnPayConfig;
import com.capstone.wellnessnavigatorgym.dto.cart.CartWithDetail;
import com.capstone.wellnessnavigatorgym.dto.payment.PaymentResponseDto;
import com.capstone.wellnessnavigatorgym.dto.payment.TransactionStatusDTO;
import com.capstone.wellnessnavigatorgym.entity.Cart;
import com.capstone.wellnessnavigatorgym.entity.CartDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @PutMapping("/create_payment")
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody CartWithDetail cartWithDetail) throws UnsupportedEncodingException {
        Cart cart = cartWithDetail.getCart();
        List<CartDetail> cartDetailList = cartWithDetail.getCartDetailList();
        int totalAmount = 0;
        Set<CartDetail> cartDetails = new HashSet<>();


        String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
//      String vnp_IpAddr = VnPayConfig.getIpAddress(req);
        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VnPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
//        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vpn_TxnRef", vnp_TxnRef);
        vnp_Params.put("vpn_OrderInfo", "Thanh Toan Don Hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_ReturnUrl);


        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setStatus("OK");
        paymentResponseDto.setMessage("Successfully");
        paymentResponseDto.setUrl(paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponseDto);
    }

    @GetMapping("/payment_info")
    public ResponseEntity<?> transaction(
            @RequestParam(value = "vpn_Amount") String amount,
            @RequestParam(value = "vpn_BankCode") String bankCode,
            @RequestParam(value = "vpn_OrderInfo") String order,
            @RequestParam(value = "vpn_ResponseCode") String responseCode) {
        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        if (responseCode.equals("00")) {
            transactionStatusDTO.setStatus("OK");
            transactionStatusDTO.setMessage("Successfully");
            transactionStatusDTO.setData("");
        } else {
            transactionStatusDTO.setStatus("No");
            transactionStatusDTO.setMessage("Failed");
            transactionStatusDTO.setData("");
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionStatusDTO);

    }
}
