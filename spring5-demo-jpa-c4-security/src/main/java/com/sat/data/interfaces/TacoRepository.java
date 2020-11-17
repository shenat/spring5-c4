package com.sat.data.interfaces;

import org.springframework.data.repository.CrudRepository;

import com.sat.bean.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long>{

}
