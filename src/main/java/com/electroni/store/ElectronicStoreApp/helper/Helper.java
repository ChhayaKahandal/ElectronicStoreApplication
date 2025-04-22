package com.electroni.store.ElectronicStoreApp.helper;

import com.electroni.store.ElectronicStoreApp.dtoclasses.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper
{
    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> page,Class<V> type)//second param is target type->says that which type of pageable you want.
    {
         //<U,V> U is entity and V is dto.(mhnje return type ha dto(userDto or other) ahe ani apn parameter mdhe entity(user,catergory or other) ghetoy)
        List<U> entity = page.getContent();//using this we get list of users according to pagesize and pagenumber.
        List<V> dtoList=entity.stream().map(object->new ModelMapper().map(object,type)).collect(Collectors.toList());//object->new ModelMapper().map(object,V) here we conevrting our entity object into dto object
        //karan apan service vrun controller la dto return krto.


        PageableResponse<V> response=new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }

}
