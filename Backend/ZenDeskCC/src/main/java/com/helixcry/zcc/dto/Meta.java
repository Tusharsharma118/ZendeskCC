package com.helixcry.zcc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Meta{
    public boolean has_more;
    public String after_cursor;
    public String before_cursor;
}
