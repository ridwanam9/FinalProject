/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wsc.UASwsc;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author DELL
 */


//Ridwan Amirul Maulana
//20200140041

@Controller
@ResponseBody
public class MyController {
    
    Biodata7JpaController dctrl = new Biodata7JpaController();
    Biodata7 data = new Biodata7();
    
    @RequestMapping("/GET")
    public String getDataById(){
        List<Biodata7> datas = new ArrayList<>();
        
        try{
            datas = dctrl.findBiodata7Entities();
        }
        catch (Exception error){}
        
        //String result = data.getName()+""+data.getBirthday().toString();
        
        return datas.toString();
    }
    
    @RequestMapping(value="/POST", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public String sentData(HttpEntity<String> kiriman) throws Exception{
        
        String json_receive = kiriman.getBody();
        String message = "no action";
        
        
        ObjectMapper mapper = new ObjectMapper();
        Biodata7 data = new Biodata7();
        
        data= mapper.readValue(json_receive, Biodata7.class);
        dctrl.create(data);
        message = data.getName() + " Saved";
        
        
        return message;
    }
    
    @RequestMapping(value="/PUT", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public String editData(HttpEntity<String> kiriman) throws Exception{
        
        String json_receive = kiriman.getBody();
        String message = "no action";
       
        ObjectMapper mapper = new ObjectMapper();
        Biodata7 newdata = new Biodata7();
        
        newdata= mapper.readValue(json_receive, Biodata7.class);
        dctrl.edit(newdata);
        message = newdata.getName() + " Saved";
        
        
        return message;
    }
    
    @RequestMapping(value="/DELETE", method = RequestMethod.DELETE, consumes = APPLICATION_JSON_VALUE)
    public String deleteData(@PathVariable("id") int id) throws Exception{
        
        
        String message = "no action";
        dctrl.destroy(id);
        message = id + " Deleted";
        return message;
    }
    
}
