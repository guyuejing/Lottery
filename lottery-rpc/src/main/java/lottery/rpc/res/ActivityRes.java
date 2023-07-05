package lottery.rpc.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lottery.common.Result;
import lottery.rpc.dto.ActivityDto;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRes implements Serializable {
    private Result result;
    private ActivityDto activity;
}
