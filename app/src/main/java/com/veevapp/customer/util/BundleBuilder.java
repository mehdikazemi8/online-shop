package com.veevapp.customer.util;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A utility class to create a <code>Bundle</code> using the builder pattern.
 *
 * @author Floern
 */
public class BundleBuilder {

    private final Bundle bundle;


    /**
     * Create a new BundleBuilder based on an empty Bundle.
     */
    public BundleBuilder() {
        bundle = new Bundle();
    }


    /**
     * Wrap a BundleBuilder around an existing Bundle.
     * The values will be inserted into the provided Bundle.
     *
     * @param source a Bundle
     */
    public BundleBuilder(Bundle source) {
        bundle = source;
    }


    /**
     * Inserts all mappings from the given Bundle into this Bundle.
     *
     * @param map a Bundle
     */
    public BundleBuilder putAll(Bundle map) {
        bundle.putAll(map);
        return this;
    }


    /**
     * Inserts a Boolean value into the mapping of this Bundle, replacing any existing value for the given key. Either key or
     * value may be null.
     *
     * @param key   a String, or null
     * @param value a Boolean, or null
     */
    public BundleBuilder putBoolean(String key, boolean value) {
        bundle.putBoolean(key, value);
        return this;
    }


    /**
     * Inserts a byte value into the mapping of this Bundle, replacing any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a byte
     */
    public BundleBuilder putByte(String key, byte value) {
        bundle.putByte(key, value);
        return this;
    }


    /**
     * Inserts a char value into the mapping of this Bundle, replacing any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a char, or null
     */
    public BundleBuilder putChar(String key, char value) {
        bundle.putChar(key, value);
        return this;
    }


    /**
     * Inserts a short value into the mapping of this Bundle, replacing any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a short
     */
    public BundleBuilder putShort(String key, short value) {
        bundle.putShort(key, value);
        return this;
    }


    /**
     * Inserts an int value into the mapping of this Bundle, replacing any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value an int, or null
     */
    public BundleBuilder putInt(String key, int value) {
        bundle.putInt(key, value);
        return this;
    }


    /**
     * Inserts a long value into the mapping of this Bundle, replacing any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a long
     */
    public BundleBuilder putLong(String key, long value) {
        bundle.putLong(key, value);
        return this;
    }


    /**
     * Inserts a float value into the mapping of this Bundle, replacing any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a float
     */
    public BundleBuilder putFloat(String key, float value) {
        bundle.putFloat(key, value);
        return this;
    }


    /**
     * Inserts a double value into the mapping of this Bundle, replacing any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a double
     */
    public BundleBuilder putDouble(String key, double value) {
        bundle.putDouble(key, value);
        return this;
    }


    /**
     * Inserts a String value into the mapping of this Bundle, replacing any existing value for the given key. Either key or
     * value may be null.
     *
     * @param key   a String, or null
     * @param value a String, or null
     */
    public BundleBuilder putString(String key, String value) {
        bundle.putString(key, value);
        return this;
    }


    /**
     * Inserts a CharSequence value into the mapping of this Bundle, replacing any existing value for the given key. Either key
     * or value may be null.
     *
     * @param key   a String, or null
     * @param value a CharSequence, or null
     */
    public BundleBuilder putCharSequence(String key, CharSequence value) {
        bundle.putCharSequence(key, value);
        return this;
    }


    /**
     * Inserts a Parcelable value into the mapping of this Bundle, replacing any existing value for the given key. Either key or
     * value may be null.
     *
     * @param key   a String, or null
     * @param value a Parcelable object, or null
     */
    public BundleBuilder putParcelable(String key, Parcelable value) {
        bundle.putParcelable(key, value);
        return this;
    }


    /**
     * Inserts an array of Parcelable values into the mapping of this Bundle, replacing any existing value for the given key.
     * Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an array of Parcelable objects, or null
     */
    public BundleBuilder putParcelableArray(String key, Parcelable[] value) {
        bundle.putParcelableArray(key, value);
        return this;
    }


    /**
     * Inserts a List of Parcelable values into the mapping of this Bundle, replacing any existing value for the given key.
     * Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList of Parcelable objects, or null
     */
    public BundleBuilder putParcelableArrayList(String key, ArrayList<? extends Parcelable> value) {
        bundle.putParcelableArrayList(key, value);
        return this;
    }


    /**
     * Inserts a SparceArray of Parcelable values into the mapping of this Bundle, replacing any existing value for the given
     * key. Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a SparseArray of Parcelable objects, or null
     */
    public BundleBuilder putSparseParcelableArray(String key, SparseArray<? extends Parcelable> value) {
        bundle.putSparseParcelableArray(key, value);
        return this;
    }


    /**
     * Inserts an ArrayList<Integer> value into the mapping of this Bundle, replacing any existing value for the given key.
     * Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList<Integer> object, or null
     */
    public BundleBuilder putIntegerArrayList(String key, ArrayList<Integer> value) {
        bundle.putIntegerArrayList(key, value);
        return this;
    }


    /**
     * Inserts an ArrayList<String> value into the mapping of this Bundle, replacing any existing value for the given key.
     * Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList<String> object, or null
     */
    public BundleBuilder putStringArrayList(String key, ArrayList<String> value) {
        bundle.putStringArrayList(key, value);
        return this;
    }


    /**
     * Inserts an ArrayList<CharSequence> value into the mapping of this Bundle, replacing any existing value for the given key.
     * Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList<CharSequence> object, or null
     */
    public BundleBuilder putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
        bundle.putCharSequenceArrayList(key, value);
        return this;
    }


    /**
     * Inserts a Serializable value into the mapping of this Bundle, replacing any existing value for the given key. Either key
     * or value may be null.
     *
     * @param key   a String, or null
     * @param value a Serializable object, or null
     */
    public BundleBuilder putSerializable(String key, Serializable value) {
        bundle.putSerializable(key, value);
        return this;
    }


    /**
     * Inserts a boolean array value into the mapping of this Bundle, replacing any existing value for the given key. Either key
     * or value may be null.
     *
     * @param key   a String, or null
     * @param value a boolean array object, or null
     */
    public BundleBuilder putBooleanArray(String key, boolean[] value) {
        bundle.putBooleanArray(key, value);
        return this;
    }


    /**
     * Inserts a byte array value into the mapping of this Bundle, replacing any existing value for the given key. Either key or
     * value may be null.
     *
     * @param key   a String, or null
     * @param value a byte array object, or null
     */
    public BundleBuilder putByteArray(String key, byte[] value) {
        bundle.putByteArray(key, value);
        return this;
    }


    /**
     * Inserts a short array value into the mapping of this Bundle, replacing any existing value for the given key. Either key
     * or value may be null.
     *
     * @param key   a String, or null
     * @param value a short array object, or null
     */
    public BundleBuilder putShortArray(String key, short[] value) {
        bundle.putShortArray(key, value);
        return this;
    }


    /**
     * Inserts a char array value into the mapping of this Bundle, replacing any existing value for the given key. Either key or
     * value may be null.
     *
     * @param key   a String, or null
     * @param value a char array object, or null
     */
    public BundleBuilder putCharArray(String key, char[] value) {
        bundle.putCharArray(key, value);
        return this;
    }


    /**
     * Inserts an int array value into the mapping of this Bundle, replacing any existing value for the given key. Either key or
     * value may be null.
     *
     * @param key   a String, or null
     * @param value an int array object, or null
     */
    public BundleBuilder putIntArray(String key, int[] value) {
        bundle.putIntArray(key, value);
        return this;
    }


    /**
     * Inserts a long array value into the mapping of this Bundle, replacing any existing value for the given key. Either key or
     * value may be null.
     *
     * @param key   a String, or null
     * @param value a long array object, or null
     */
    public BundleBuilder putLongArray(String key, long[] value) {
        bundle.putLongArray(key, value);
        return this;
    }


    /**
     * Inserts a float array value into the mapping of this Bundle, replacing any existing value for the given key. Either key
     * or value may be null.
     *
     * @param key   a String, or null
     * @param value a float array object, or null
     */
    public BundleBuilder putFloatArray(String key, float[] value) {
        bundle.putFloatArray(key, value);
        return this;
    }


    /**
     * Inserts a double array value into the mapping of this Bundle, replacing any existing value for the given key. Either key
     * or value may be null.
     *
     * @param key   a String, or null
     * @param value a double array object, or null
     */
    public BundleBuilder putDoubleArray(String key, double[] value) {
        bundle.putDoubleArray(key, value);
        return this;
    }


    /**
     * Inserts a String array value into the mapping of this Bundle, replacing any existing value for the given key. Either key
     * or value may be null.
     *
     * @param key   a String, or null
     * @param value a String array object, or null
     */
    public BundleBuilder putStringArray(String key, String[] value) {
        bundle.putStringArray(key, value);
        return this;
    }


    /**
     * Inserts a CharSequence array value into the mapping of this Bundle, replacing any existing value for the given key.
     * Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a CharSequence array object, or null
     */
    public BundleBuilder putCharSequenceArray(String key, CharSequence[] value) {
        bundle.putCharSequenceArray(key, value);
        return this;
    }


    /**
     * Inserts a Size value into the mapping of this Bundle, replacing any existing value for the given key. Either key or value
     * may be null.
     *
     * @param key   a String, or null
     * @param value a Size object, or null
     * @since API level 21
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BundleBuilder putSize(String key, Size value) {
        bundle.putSize(key, value);
        return this;
    }


    /**
     * Inserts a SizeF value into the mapping of this Bundle, replacing any existing value for the given key. Either key or
     * value may be null.
     *
     * @param key   a String, or null
     * @param value a SizeF object, or null
     * @since API level 21
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BundleBuilder putSizeF(String key, SizeF value) {
        bundle.putSizeF(key, value);
        return this;
    }


    /**
     * Inserts a Bundle value into the mapping of this Bundle, replacing any existing value for the given key. Either key or
     * value may be null.
     *
     * @param key   a String, or null
     * @param value a Bundle object, or null
     */
    public BundleBuilder putBundle(String key, Bundle value) {
        bundle.putBundle(key, value);
        return this;
    }


    /**
     * Get the Bundle.
     *
     * @return built Bundle instance
     */
    public Bundle build() {
        return bundle;
    }

}
