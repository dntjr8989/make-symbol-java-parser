#include "ai_serenade_treesitter_TreeSitter.h"
#include "ai_serenade_treesitter_Node.h"
#include <jni.h>
#include <string.h>
#include <tree_sitter/api.h>


JNIEXPORT jobject JNICALL Java_ai_serenade_treesitter_Node_getChildByFieldName(
  JNIEnv* env, jobject thisObject, jstring name) {

  if (name == NULL) {
    return NULL;
  }

  uint32_t length = env->GetStringLength(name);
  const char* childName = env->GetStringUTFChars(name, NULL);
  TSNode node = _unmarshalNode(env, thisObject); //
  TSNode child = ts_node_child_by_field_name(node, childName, length);
  if (ts_node_is_null(child)) return NULL;
  jobject childObject = _marshalNode(env, child); //
  //__copyTree(env, thisObject, childObject); //
  return childObject;
}

JNIEXPORT jobject JNICALL Java_ai_serenade_treesitter_Node_getStartPoint(
  JNIEnv* env, jobject thisObject) {
  TSNode node = _unmarshalNode(env, thisObject);
  if (ts_node_is_null(node)) return env->CallStaticObjectMethod(_pointClass, _pointOriginStaticMethod);
  TSPoint point = ts_node_start_point(node);
  return _marshalPoint(env, point);
}

JNIEXPORT jobject JNICALL Java_ai_serenade_treesitter_Node_getEndPoint(
  JNIEnv* env, jobject thisObject) {
  TSNode node = _unmarshalNode(env, thisObject);
  if (ts_node_is_null(node)) return env->CallStaticObjectMethod(_pointClass, _pointOriginStaticMethod);
  TSPoint point = ts_node_end_point(node);
  return _marshalPoint(env, point);
}

JNIEXPORT jobject JNICALL Java_ai_serenade_treesitter_Node_getParent(
  JNIEnv* env, jobject thisObject) {
  TSNode node = _unmarshalNode(env, thisObject);
  TSNode parent = ts_node_parent(node);
  if (ts_node_is_null(parent)) return NULL;
  jobject parentObject = _marshalNode(env, parent);
  //__copyTree(env, thisObject, parentObject);
  return parentObject;
}
