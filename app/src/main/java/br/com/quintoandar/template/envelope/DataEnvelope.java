package br.com.quintoandar.template.envelope;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class DataEnvelope {

    @SerializedName("other_property_name")
    String name;
}
