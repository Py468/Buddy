package net.enjoy.springboot.registrationlogin.config;

import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ImageConfig {
    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
    @Bean
    public Cloudinary getCloudinary(){
        Map config=new HashMap();
        config.put("cloud_name","");
        config.put("api_key","");
        config.put("api_secret","");
        config.put("secure",true);
        return new Cloudinary(config);

    }

}
