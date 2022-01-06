package com.example.paymentutil.response;

import com.example.paymentutil.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 * @author Khan Hafizur Rahman
 * @since 3/1/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse implements Serializable {
    private String errorCode;
    @Builder.Default
    private String updateTime = DateUtil.formattedDateTime();

}
