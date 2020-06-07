package c.b.s.common.message.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by guiqingqing on 2018/8/18.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingResponse<T> {
    private Integer totalCount;
    private List<T> content;
}