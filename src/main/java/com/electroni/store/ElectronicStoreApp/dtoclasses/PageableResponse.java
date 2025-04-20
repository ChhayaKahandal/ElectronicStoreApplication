package com.electroni.store.ElectronicStoreApp.dtoclasses;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponse<T>
{
    /*jar apn List<T> tr pageable he fct user api sathich responsible/use hoil
    pan aplyala to saglya apis sathi(category etc) use krycha ahe tyamule apn tyala generic type cha banvla*/
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage ;
}
