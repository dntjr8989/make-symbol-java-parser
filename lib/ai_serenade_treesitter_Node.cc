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
