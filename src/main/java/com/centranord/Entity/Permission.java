package com.centranord.Entity;

import com.centranord.Enum.ActionPermission;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Permission {
    @Id
    private String id;
    private String resourceName;
    private ActionPermission action;


}
