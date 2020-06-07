package c.b.s.common.util.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by guiqingqing on 2019/7/18.
 */
@Data
@NoArgsConstructor
public class TypeMappingMap {
    private List<TypeMapping> mappings;
}