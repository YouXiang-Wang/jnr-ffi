package jnr.ffi.provider.converters;

import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.provider.ParameterFlags;

/**
 * Converts a Byte[] array to a byte[] array parameter
 */
@ToNativeConverter.NoContext
public class ByteArrayParameterConverter implements ToNativeConverter<Byte[], byte[]>, ToNativeConverter.PostInvocation<Byte[], byte[]> {
    private final jnr.ffi.Runtime runtime;
    private final int parameterFlags;

    public static ToNativeConverter<Byte[], byte[]> getInstance(jnr.ffi.Runtime runtime, int parameterFlags) {
        return new ByteArrayParameterConverter(runtime, parameterFlags);
    }

    public ByteArrayParameterConverter(jnr.ffi.Runtime runtime, int parameterFlags) {
        this.runtime = runtime;
        this.parameterFlags = parameterFlags;
    }

    @Override
    public byte[] toNative(Byte[] array, ToNativeContext context) {
        if (array == null) {
            return null;
        }
        byte[] primitive = new byte[array.length];
        if (ParameterFlags.isIn(parameterFlags)) {
            for (int i = 0; i < array.length; i++) {
                primitive[i] = array[i] != null ? array[i] : 0;
            }
        }

        return primitive;
    }

    @Override
    public void postInvoke(Byte[] array, byte[] primitive, ToNativeContext context) {
        if (array != null && primitive != null && ParameterFlags.isOut(parameterFlags)) {
            for (int i = 0; i < array.length; i++) {
                array[i] = primitive[i];
            }
        }
    }

    @Override
    public Class<byte[]> nativeType() {
        return byte[].class;
    }
}
