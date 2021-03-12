use std::{convert::TryInto, mem::MaybeUninit};

use jni::objects::JObject;
use jni::sys::jlong;
use jni::JNIEnv;

pub mod ffi;

#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_sparse4s_GrBType_grbBoolean(
    _env: JNIEnv<'static>,
    _class: JObject,
) -> jlong {
    unsafe {ffi::GrB_BOOL as jlong}
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_sparse4s_GrB_init(_env: JNIEnv<'static>, _class: JObject) -> jlong {
    let grb_info = unsafe { ffi::GrB_init(ffi::GrB_Mode_GrB_NONBLOCKING) };
    grb_info as jlong
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_sparse4s_GrB_Finalize(_env: JNIEnv<'static>, _class: JObject) -> jlong {
    let grb_info = unsafe { ffi::GrB_finalize() };
    grb_info as jlong
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_sparse4s_GrB_createMatrix(_env: JNIEnv<'static>,  _class: JObject, tpe: jlong, rows:jlong, cols: jlong) -> jlong {
    let mut mat = MaybeUninit::<ffi::GrB_Matrix>::uninit();

    let tpe = tpe as ffi::GrB_Type;

    let code = unsafe {ffi::GrB_Matrix_new(mat.as_mut_ptr(), tpe, rows.try_into().unwrap(), cols.try_into().unwrap())};
    match code {
        0 => mat.as_ptr() as jlong,
        err => err.into()
    }
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_sparse4s_GrB_freeMatrix(_env: JNIEnv<'static>, _class: JObject, mat_ptr: jlong) -> jlong {
    let mat = mat_ptr as *mut ffi::GrB_Matrix;

    let code = unsafe{ ffi::GrB_Matrix_free(mat) };
    code.into()
}
