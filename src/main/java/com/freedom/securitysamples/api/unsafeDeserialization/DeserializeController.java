package com.freedom.securitysamples.api.unsafeDeserialization;

import ognl.OgnlException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class DeserializeController {
    @GetMapping("/deserialize/bad01")
    public String deserializePayload(String serializedData) throws OgnlException, IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedData.getBytes(StandardCharsets.UTF_8));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object deserializedObject = objectInputStream.readObject();
        return  "{'msg':'false'}";
    }
}