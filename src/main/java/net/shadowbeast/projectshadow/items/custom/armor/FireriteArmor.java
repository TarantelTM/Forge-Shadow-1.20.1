package net.shadowbeast.projectshadow.items.custom.armor;

import com.google.common.collect.ImmutableMap;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.shadowbeast.projectshadow.enums.ArmorStats;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FireriteArmor extends ArmorItem {
    public static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>())
                    .put(ArmorStats.FIRERITE, new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200,
                            0, false, false, false)).build();
    private FireriteArmor(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }
    public static FireriteArmor getInstance(Type ptype) {
        return new FireriteArmor(ArmorStats.FIRERITE, ptype, new Properties().stacksTo(1));
    }
    @SuppressWarnings("removal")
    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if(!world.isClientSide()) {
            if(hasFullSuitOfArmorOn(player)) {
                evaluateArmorEffects(player);
            }
        }
    }
    private void evaluateArmorEffects(Player player) {
        for(Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            MobEffectInstance mapStatusEffect = entry.getValue();

            if(hasCorrectArmorOn(player)) {
                addStatusEffectForMaterial(player, mapStatusEffect);
            }
        }
    }
    private void addStatusEffectForMaterial(Player player, MobEffectInstance pEffect) {
        boolean hasPlayerEffect = player.hasEffect(pEffect.getEffect());

        if(hasCorrectArmorOn(player) && !hasPlayerEffect) {
            player.addEffect(pEffect);
        }
    }
    private boolean hasCorrectArmorOn(Player player) {
        for(ItemStack armorStack : player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }
        ArmorItem helmet = ((ArmorItem) player.getInventory().getArmor(0).getItem());
        ArmorItem chestplate = ((ArmorItem) player.getInventory().getArmor(1).getItem());
        ArmorItem leggings = ((ArmorItem) player.getInventory().getArmor(2).getItem());
        ArmorItem boots = ((ArmorItem) player.getInventory().getArmor(3).getItem());

        return helmet.getMaterial() == ArmorStats.FIRERITE && chestplate.getMaterial() == ArmorStats.FIRERITE
                && leggings.getMaterial() == ArmorStats.FIRERITE && boots.getMaterial() == ArmorStats.FIRERITE;
    }
    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack helmet = player.getInventory().getArmor(0);
        ItemStack chestplate = player.getInventory().getArmor(1);
        ItemStack leggings = player.getInventory().getArmor(2);
        ItemStack boots = player.getInventory().getArmor(3);

        return !helmet.isEmpty() && !chestplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.projectshadow.firerite_set.shift"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.projectshadow.shift_for_info"));
        }
    }
}
