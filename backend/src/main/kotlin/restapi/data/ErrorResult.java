package restapi.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ErrorResult implements Serializable {

    private long code;
    private String msg;
}
