package com.centranord.Entity;



import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "reclamations")
public class Reclamation {

    @Id
    private String id;

    private String sujet;
    private String description;
    private String statut;
    private String priorite;

    @Field("dateCreation")
    @CreatedDate
    private LocalDateTime dateCreation;

    @Field("dateModification")
    @LastModifiedDate
    private LocalDateTime dateModification;







}
