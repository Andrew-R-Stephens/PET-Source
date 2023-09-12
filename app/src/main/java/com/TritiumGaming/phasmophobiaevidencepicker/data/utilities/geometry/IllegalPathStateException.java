/*
 * Copyright (c) 1997, 2021, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry;

import java.io.Serial;

/**
 * The {@code IllegalPathStateException} represents an
 * exception that is thrown if an operation is performed on a path
 * that is in an illegal state with respect to the particular
 * operation being performed, such as appending a path segment
 * to a without an initial moveto.
 *
 */

public class IllegalPathStateException extends RuntimeException {

    /**
     * Use serialVersionUID from JDK 9 for interoperability.
     */
    private static final long serialVersionUID = -5158084205220481094L;

    /**
     * Constructs an {@code IllegalPathStateException} with no
     * detail message.
     *
     * @since   1.2
     */
    public IllegalPathStateException() {
    }

    /**
     * Constructs an {@code IllegalPathStateException} with the
     * specified detail message.
     * @param   s   the detail message
     * @since   1.2
     */
    public IllegalPathStateException(String s) {
        super (s);
    }
}
