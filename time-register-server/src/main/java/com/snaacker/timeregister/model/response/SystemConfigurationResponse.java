package com.snaacker.timeregister.model.response;

import com.snaacker.timeregister.persistent.SystemConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfigurationResponse {

    String name;
    String value;

    public SystemConfigurationResponse(SystemConfiguration systemConfiguration) {
        this.name = systemConfiguration.getName().toString();
        this.value = systemConfiguration.getValue();
    }
}
