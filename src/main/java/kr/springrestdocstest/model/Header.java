package kr.springrestdocstest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

    private String resultCode;
    private T data;

    public static <T> Header<T> OK() {
        return (Header<T>) Header
                .builder()
                .resultCode("ok")
                .build();
    }

    public static <T> Header<T> OK(T data) {
        return (Header<T>) Header
                .builder()
                .resultCode("ok")
                .data(data)
                .build();
    }

    public static <T> Header<T> ERROR() {
        return (Header<T>) Header
                .builder()
                .resultCode("ERROR")
                .build();
    }


}
