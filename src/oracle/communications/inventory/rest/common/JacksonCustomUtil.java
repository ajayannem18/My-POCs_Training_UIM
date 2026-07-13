/*
**
** Copyright (c) 2023, Oracle and/or its affiliates. All rights reserved.
**
** The Universal Permissive License (UPL), Version 1.0
**
** Subject to the condition set forth below, permission is hereby granted to any
** person obtaining a copy of this software, associated documentation and/or data
** (collectively the "Software"), free of charge and under any and all copyright
** rights in the Software, and any and all patent rights owned or freely
** licensable by each licensor hereunder covering either (i) the unmodified
** Software as contributed to or provided by such licensor, or (ii) the Larger
** Works (as defined below), to deal in both
** 
** (a) the Software, and
** (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
** one is included with the Software (each a "Larger Work" to which the Software
** is contributed by such licensors),
** 
** without restriction, including without limitation the rights to copy, create
** derivative works of, display, perform, and distribute the Software and make,
** use, sell, offer for sale, import, export, have made, and have sold the
** Software and the Larger Work(s), and to sublicense the foregoing rights on
** either these or other terms.
** 
** This license is subject to the following condition:
** The above copyright notice and either this complete permission notice or at
** a minimum a reference to the UPL must be included in all copies or
** substantial portions of the Software.
** 
** THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
** IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
** FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
** AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
** LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
** OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
** SOFTWARE.
*/

package oracle.communications.inventory.rest.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import oracle.communications.inventory.api.entity.LogicalDevice;


public class JacksonCustomUtil {
 
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
 
    public static <T> T fromString(String string, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(string, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: "
                + string + " cannot be transformed to Json object");
        }
    }
 
    public static String toString(Object value) {
        try {
            if (value instanceof String) 
                return  (String) value;
            else 
                return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json object value: "
                + value + " cannot be transformed to a String");
        }
    }
 
    public static JsonNode toJsonNode(String value) {
        try {
            OBJECT_MAPPER.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
            return OBJECT_MAPPER.readTree(value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
 
    public static <T> T clone(T value) {
        return fromString(toString(value), (Class<T>) value.getClass());
    }
    
    
    public static String getDiscriminatorClass(Object value) throws IOException {
        String jsonStr = toString(value);
        OBJECT_MAPPER.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        JsonNode root = OBJECT_MAPPER.readTree(jsonStr);
//        String entityClass = root.at("/entityClass").textValue();
        String entityClass = root.at("/discriminator").textValue();

        return entityClass;
    }
    
    public static String getAttributeValue(Object value, String attributeName) throws IOException {
        String jsonStr = toString(value);
        OBJECT_MAPPER.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        JsonNode root = OBJECT_MAPPER.readTree(jsonStr);
        String attributeValue = root.at("/" + attributeName).textValue();

        return attributeValue;
    }
    
    public static JsonNode getAttributeValueAsJsonNode(Object value, String attributeName) throws IOException {
        String jsonStr = toString(value);
        OBJECT_MAPPER.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        JsonNode root = OBJECT_MAPPER.readTree(jsonStr);
        JsonNode attributeValue = root.at("/" + attributeName);

        return attributeValue;
    }
    
    public static <T> T convertValuetoJson(Object value, Class<T> subClass) throws IOException {

          String strValue;
          if (value instanceof String) {
              // Cast object to string
              strValue = (String) value;
          } else {
              //Get the String representation of the JSON object
              strValue = new ObjectMapper().writeValueAsString(value);  
          }

          //Configure deserialization in order to handle enum values in the json string
          ObjectMapper objectMapper = new ObjectMapper();
          objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

          return objectMapper.readValue((String) strValue, subClass);
      }
}