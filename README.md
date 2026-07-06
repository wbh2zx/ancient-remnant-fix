# Ancient Remnant Fix

Minecraft Forge 1.20.1 Mixin 微型补丁。  
修复使用 **愚者（The Fool / Age of Mythology）** 变身模组变身为 **灾变（L_Ender's Cataclysm）** 的远古遗魂时，冲撞技能错误破坏远古残骸的问题。

## 涉及模组

| Mod | 中文名 | 版本 | 作用 |
|-----|--------|------|------|
| L_Ender's Cataclysm | 灾变 | 3.16 | 提供远古遗魂实体和冲撞技能 |
| Age of Mythology | 愚者 / The Fool | 0.2.1 | 提供变身系统，内含 bettermorph |

## 问题

远古遗魂的官方设定是：冲撞遇到远古残骸会停下。但使用愚者变身成古遗魂后，冲撞会把远古残骸直接破坏。

根因：愚者的变身系统在 `MorphUtil.ChargeBlockBreaking()` 中**自己重新实现**了一套方块破坏逻辑，完全绕过了灾变原版的同名方法。这套自实现缺少对远古残骸的保护。

## 修复方式

Mixin 注入 `MorphUtil.ChargeBlockBreaking(LivingEntity, double, boolean)`，在方法入口扫描实体周围的方块。检测到远古残骸时，取消本次方块破坏并将实体水平速度归零。

## 安装

将 `ancient_remnant_fix-1.0.0.jar` 放入 `mods` 文件夹即可。

## 兼容

- Minecraft 1.20.1
- Forge 47.x
- 灾变 (L_Ender's Cataclysm) 3.16
- 愚者 (Age of Mythology) 0.2.1

## 构建

```bash
./gradlew build
```

需在 `libs/` 下放置：
- `L_Enders_Cataclysm-3.16.jar`
- `ageofmythology-0.2.1-all.jar`

## License

MIT
