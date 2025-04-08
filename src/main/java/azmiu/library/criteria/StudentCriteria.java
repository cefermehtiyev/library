package azmiu.library.criteria;

import azmiu.library.model.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentCriteria extends UserCriteria {
    private String specialization;
    private Integer courseFrom;
    private Integer courseTo;
    private String groupName;
}
