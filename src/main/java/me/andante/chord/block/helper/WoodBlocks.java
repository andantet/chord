package me.andante.chord.block.helper;

import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

import me.andante.chord.block.CSignBlock;
import me.andante.chord.block.CWallSignBlock;
import me.andante.chord.block.vanilla.*;
import me.andante.chord.entity.boat.CBoatEntity;
import me.andante.chord.entity.boat.CBoatInfo;
import me.andante.chord.item.CBoatItem;
import me.andante.chord.util.CModInstance;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class WoodBlocks {
    private final CModInstance MOD_INSTANCE;

    private final String id;
    private final boolean flammable;
    private final Identifier signTextureIdentifier;
    private final int leafItemColor;

    public final Block PLANKS;
    public final Block SAPLING;
    public final Block LOG;
    public final Block STRIPPED_LOG;
    public final Block STRIPPED_WOOD;
    public final Block WOOD;
    public final Block LEAVES;
    public final Block SLAB;
    public final Block PRESSURE_PLATE;
    public final Block FENCE;
    public final Block TRAPDOOR;
    public final Block FENCE_GATE;
    public final Block STAIRS;
    public final Block BUTTON;
    public final Block DOOR;
    public final Block SIGN;
    public final Block WALL_SIGN;

    public final Item SIGN_ITEM;
    public final Item BOAT_ITEM;

    public final EntityType<CBoatEntity> BOAT_ENTITY;

    private WoodBlocks(CModInstance modInstance, String id, boolean flammable, int leafItemColor, SaplingGenerator saplingGenerator, BoatEntity.Type boatType, PressurePlateBlock.ActivationRule pressurePlateActivationRule) {
        this.MOD_INSTANCE = modInstance;
        String modId = modInstance.getModId();
        ItemGroup itemGroup = modInstance.getItemGroup();

        this.id = id;
        this.flammable = flammable;
        this.leafItemColor = leafItemColor;
        this.signTextureIdentifier = new Identifier(modId, "entity/sign/" + id);

        this.PLANKS = modInstance.register(id + "_planks", new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS)));
        this.SAPLING = modInstance.register(id + "_sapling", new PublicSaplingBlock(saplingGenerator, FabricBlockSettings.copy(Blocks.OAK_SAPLING)));
        this.LOG = modInstance.register(id + "_log", new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG)));
        this.STRIPPED_LOG = modInstance.register("stripped_" + id + "_log", new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_LOG)));
        this.STRIPPED_WOOD = modInstance.register("stripped_" + id + "_wood", new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_WOOD)));
        this.WOOD = modInstance.register(id + "_wood", new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_WOOD)));
        this.LEAVES = modInstance.register(id + "_leaves", new LeavesBlock(FabricBlockSettings.copy(Blocks.OAK_LEAVES)));
        this.SLAB = modInstance.register(id + "_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)));
        this.PRESSURE_PLATE = modInstance.register(id + "_pressure_plate", new PublicPressurePlateBlock(pressurePlateActivationRule, FabricBlockSettings.copy(Blocks.OAK_PRESSURE_PLATE)));
        this.FENCE = modInstance.register(id + "_fence", new FenceBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE)));
        this.TRAPDOOR = modInstance.register(id + "_trapdoor", new PublicTrapdoorBlock(FabricBlockSettings.copy(Blocks.OAK_TRAPDOOR)));
        this.FENCE_GATE = modInstance.register(id + "_fence_gate", new FenceGateBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE_GATE)));
        this.STAIRS = modInstance.register(id + "_stairs", new PublicStairsBlock(this.PLANKS.getDefaultState(), FabricBlockSettings.copy(Blocks.OAK_STAIRS)));
        this.BUTTON = modInstance.register(id + "_button", new PublicWoodenButtonBlock(FabricBlockSettings.copy(Blocks.OAK_BUTTON)));
        this.DOOR = modInstance.register(id + "_door", new PublicDoorBlock(FabricBlockSettings.copy(Blocks.OAK_DOOR)));

        this.SIGN = modInstance.register(id + "_sign", new CSignBlock(this.getSignTextureIdentifier(), FabricBlockSettings.copy(Blocks.OAK_SIGN)), false);
        this.WALL_SIGN = modInstance.register(id + "_wall_sign", new CWallSignBlock(this.getSignTextureIdentifier(), FabricBlockSettings.copy(Blocks.OAK_WALL_SIGN)), false);
        SIGN_ITEM = modInstance.register(id + "_sign", new SignItem(new Item.Settings().maxCount(16).group(itemGroup), this.SIGN, this.WALL_SIGN));

        BOAT_ITEM = modInstance.register(id + "_boat", new CBoatItem(new Supplier<EntityType<CBoatEntity>>(){
            @Override
            public EntityType<CBoatEntity> get() {
                return BOAT_ENTITY;
            }
        }, new Item.Settings().maxCount(1).group(itemGroup)));;
        BOAT_ENTITY = Registry.register(Registry.ENTITY_TYPE, new Identifier(modId, id + "_boat"), FabricEntityTypeBuilder.<CBoatEntity>create(SpawnGroup.MISC, (entity, world) -> new CBoatEntity(entity, world, new CBoatInfo(this.BOAT_ITEM, this.PLANKS.asItem(), new Identifier(modId, "textures/entity/boat/" + id + ".png"), boatType))).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).build());

        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(this.LEAVES.asItem(), 0.3F);
        if (this.isFlammable()) {
            // flammable blocks
            int baseBurnChance = 5;
            int largeBurnChance = baseBurnChance * 6;

            int baseSpreadChance = 20;
            int smallSpreadChance = baseSpreadChance / 4;
            int largeSpreadChance = baseSpreadChance * 3;

            FlammableBlockRegistry fbrInstance = FlammableBlockRegistry.getDefaultInstance();
            fbrInstance.add(this.PLANKS, baseBurnChance, baseSpreadChance);
            fbrInstance.add(this.SLAB, baseBurnChance, baseSpreadChance);
            fbrInstance.add(this.FENCE_GATE, baseBurnChance, baseSpreadChance);
            fbrInstance.add(this.FENCE, baseBurnChance, baseSpreadChance);
            fbrInstance.add(this.STAIRS, baseBurnChance, baseSpreadChance);
            fbrInstance.add(this.LOG, baseBurnChance, smallSpreadChance);
            fbrInstance.add(this.STRIPPED_LOG, baseBurnChance, smallSpreadChance);
            fbrInstance.add(this.STRIPPED_WOOD, baseBurnChance, smallSpreadChance);
            fbrInstance.add(this.WOOD, baseBurnChance, smallSpreadChance);
            fbrInstance.add(this.LEAVES, largeBurnChance, largeSpreadChance);

            // fuel registering
            int fenceFuelTime = 300;

            FuelRegistry frInstance = FuelRegistry.INSTANCE;
            frInstance.add(this.FENCE, fenceFuelTime);
            frInstance.add(this.FENCE_GATE, fenceFuelTime);
        }

        new ImmutableMap.Builder<Block, Block>()
            .put(this.LOG, this.STRIPPED_LOG)
            .put(this.WOOD, this.STRIPPED_WOOD)
            .build().forEach((base, result) -> {
                UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
                    if (player.getStackInHand(hand).getItem().isIn(FabricToolTags.AXES) && world.getBlockState(hit.getBlockPos()).getBlock() == base) {
                        BlockPos blockPos = hit.getBlockPos();
                        BlockState blockState = world.getBlockState(blockPos);

                        world.playSound(player, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        if (!world.isClient) {
                            world.setBlockState(blockPos, result.getDefaultState().with(PillarBlock.AXIS, blockState.get(PillarBlock.AXIS)), 11);
                            if (!player.isCreative()) {
                                ItemStack stack = player.getStackInHand(hand);
                                stack.damage(1, player, ((p) -> p.sendToolBreakStatus(hand)));
                            }
                        }

                        return ActionResult.SUCCESS;
                    }

                    return ActionResult.PASS;
                });
            });
    }

    public CModInstance getModInstance() {
        return this.MOD_INSTANCE;
    }

    public String getId() {
        return this.id;
    }
    public boolean isFlammable() {
        return this.flammable;
    }
    public int getLeafItemColor() {
        return this.leafItemColor;
    }
    public Identifier getSignTextureIdentifier() {
        return this.signTextureIdentifier;
    }

    public static class Builder {
        private boolean flammable = true;
        private int leafItemColor = 0;
        private SaplingGenerator saplingGenerator = new OakSaplingGenerator();
        private BoatEntity.Type boatType = BoatEntity.Type.OAK;
        private PressurePlateBlock.ActivationRule pressurePlateActivationRule = PressurePlateBlock.ActivationRule.EVERYTHING;

        public Builder() {}

        public WoodBlocks.Builder nonFlammable() {
            this.flammable = false;
            return this;
        }
        public WoodBlocks.Builder leafItemColor(int color) {
            this.leafItemColor = color;
            return this;
        }
        public WoodBlocks.Builder saplingGenerator(SaplingGenerator saplingGenerator) {
            this.saplingGenerator = saplingGenerator;
            return this;
        }
        public WoodBlocks.Builder boatType(BoatEntity.Type boatType) {
            this.boatType = boatType;
            return this;
        }
        public WoodBlocks.Builder pressurePlateActivationRule(PressurePlateBlock.ActivationRule pressurePlateActivationRule) {
            this.pressurePlateActivationRule = pressurePlateActivationRule;
            return this;
        }

        public WoodBlocks build(CModInstance info, String id) {
            return new WoodBlocks(info, id, this.flammable, this.leafItemColor, this.saplingGenerator, this.boatType, this.pressurePlateActivationRule);
        }
    }
}
