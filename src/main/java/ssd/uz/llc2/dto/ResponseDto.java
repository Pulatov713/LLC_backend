//package ssd.uz.llc.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class ResponseDto<T> {
//    private boolean success;
//    private String message;
//    private T data;
//
//    public ResponseDto(boolean success, String message) {
//        this.success = success;
//        this.message = message;
//    }
//}


package ssd.uz.llc2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDto<T> {
    private boolean success;
    private String message;
    private T data;

    public ResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
