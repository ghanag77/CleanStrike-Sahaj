package exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OutOfCoinException extends RuntimeException {

    public OutOfCoinException(String message){
        super(message);

    }
}
