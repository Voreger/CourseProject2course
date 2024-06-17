package api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorData {
    private int id;
    private String name;
    private int year;
    private String color;

    @JsonProperty("pantone_value")
    private String pantoneValue;
}