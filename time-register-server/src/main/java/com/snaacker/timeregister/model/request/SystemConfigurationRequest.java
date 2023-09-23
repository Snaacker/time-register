package com.snaacker.timeregister.model.request;

import com.snaacker.timeregister.persistent.ConfigurationName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfigurationRequest {
    ConfigurationName configurationName;
    String value;
}
