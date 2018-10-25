package com.lvqiu.dao.imp;

import com.lvqiu.dao.humanDao;
import com.lvqiu.intent_apt.annotations.XService;

import java.util.ArrayList;
import java.util.List;

@XService
public class humanDaoImp implements humanDao {

    public List<String> getAllHuman(){
        List<String> list=new ArrayList<>();
        for (int i=0;i<5;i++){
            list.add("I am human"+i);
        }
        return list;
    }
}
