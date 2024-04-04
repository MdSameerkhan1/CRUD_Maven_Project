package com.hibernate.CRUD.CRUD;

import java.util.Iterator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;


@Data
@Entity
@Table(name="student_crud")
public class Student {
  @Id
  long studentid;
  String firstname,lastname,email,mobile;

  
}
