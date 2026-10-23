import os, re

# 目标：全面统一为 haoirclient (h-a-o-i-r-c-l-i-e-n-t)
GOOD_PKG = "haoirdevelopment.haoirclient"
WRONG_PKGS = [
    "haoirdevelopment.haohirclient", # 多 h
    "haoirdevelopment.haorclient"    # 少 i
]

# 1. 处理物理文件夹
base_java = "src/main/java/haoirdevelopment"
if os.path.exists(base_java):
    # 清理错误文件夹，合并到正确文件夹
    for wrong in ["haohirclient", "haorclient"]:
        wrong_dir = os.path.join(base_java, wrong)
        good_dir = os.path.join(base_java, "haoirclient")
        if os.path.exists(wrong_dir):
            os.makedirs(good_dir, exist_ok=True)
            os.system(f"cp -rn {wrong_dir}/. {good_dir}/")
            os.system(f"rm -rf {wrong_dir}")
            print(f"✅ 文件夹已修复: {wrong} -> haoirclient")

# 2. 处理文件内容
fixed_count = 0
for root, dirs, files in os.walk("src/main/java"):
    for file in files:
        if file.endswith(".java"):
            path = os.path.join(root, file)
            with open(path, "r", encoding="utf-8") as f:
                content = f.read()
            
            original = content
            for wrong in WRONG_PKGS:
                content = content.replace(wrong, GOOD_PKG)
            
            if content != original:
                with open(path, "w", encoding="utf-8") as f:
                    f.write(content)
                fixed_count += 1

print(f"✅ 修复了 {fixed_count} 个 Java 文件的 import 和 package 声明")

# 3. 终极校验
mismatch = 0
for root, dirs, files in os.walk("src/main/java"):
    for file in files:
        if file.endswith(".java"):
            path = os.path.join(root, file)
            rel_path = path.replace("src/main/java/", "")
            expected_pkg = os.path.dirname(rel_path).replace("/", ".")
            
            with open(path, "r", encoding="utf-8") as f:
                first_line = f.readline()
                if "package " in first_line:
                    actual_pkg = first_line.split("package ")[1].split(";")[0].strip()
                    if actual_pkg != expected_pkg:
                        print(f"❌ 路径与包名不匹配: {path} (期望: {expected_pkg}, 实际: {actual_pkg})")
                        mismatch += 1

if mismatch == 0:
    print("\n🎉 完美！所有文件路径和 package 声明 100% 匹配！绝对没有多 h 或者少 i！")
else:
    print(f"\n⚠️ 还有 {mismatch} 个文件不匹配，请检查！")
