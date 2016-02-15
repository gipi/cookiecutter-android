package {{ cookiecutter.package_name }}.envelope;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class DataEnvelope {

    @SerializedName("other_property_name")
    String name;
}
