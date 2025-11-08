package com.umg.lrperezc.util;

import com.umg.lrperezc.facturas.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ComponentAClient {

    private final RestTemplate restTemplate;

    @Value("${componenta.base-url:http://localhost:8080}")
    private String baseUrl;

    public List<ItemDTO> obtenerItemsPendientesPorCliente(Long clientId){
        try {
            String url = baseUrl + "/pedidos/client/" + clientId + "/pendientes";
            List<?> response = restTemplate.getForObject(url, List.class);
            List<ItemDTO> items = new ArrayList<>();
            if(response != null){
                for(Object o : response){
                    if(o instanceof Map<?,?> map){
                        Object itemsObj = map.get("items");
                        if(itemsObj instanceof List<?> list){
                            for(Object it : list){
                                if(it instanceof Map<?,?> itm){
                                    ItemDTO dto = new ItemDTO();
                                    Object title = itm.get("title");
                                    Object price = itm.get("price");
                                    if(title != null){ dto.setTitle(title.toString()); }
                                    if(price != null){ dto.setPrice(new BigDecimal(price.toString())); }
                                    if(dto.getTitle() != null && dto.getPrice() != null){
                                        items.add(dto);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return items;
        } catch (Exception ex){
            return List.of();
        }
    }
}
