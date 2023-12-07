
package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;


@Documented @Target(CONSTRUCTOR) @Retention(RUNTIME)
public @interface ConstructorProperties {

    String[] value();
}
