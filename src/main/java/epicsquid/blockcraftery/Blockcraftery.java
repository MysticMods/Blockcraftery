package epicsquid.blockcraftery;

import epicsquid.blockcraftery.proxy.CommonProxy;
import epicsquid.mysticallib.MysticalLib;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Blockcraftery.MODID, version = Blockcraftery.VERSION, name = Blockcraftery.MODNAME, dependencies = "required-after:mysticallib@[" + MysticalLib.VERSION + ",)")
public class Blockcraftery {
  public static final String MODID = "blockcraftery";
  public static final String VERSION = "@VERSION@";
  public static final String MODNAME = "Blockcraftery";

  @SidedProxy(clientSide = "epicsquid.blockcraftery.proxy.ClientProxy", serverSide = "epicsquid.blockcraftery.proxy.CommonProxy")
  public static CommonProxy proxy;

  @Instance public static Blockcraftery INSTANCE;

  public static ModContainer CONTAINER;

  public static CreativeTabs tab = new CreativeTabs("blockcraftery") {
    @Override
    public String getTabLabel() {
      return "blockcraftery";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
      return new ItemStack(RegistryManager.editable_block, 1);
    }
  };

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    CONTAINER = Loader.instance().activeModContainer();
    MinecraftForge.EVENT_BUS.register(new RegistryManager());
    MinecraftForge.EVENT_BUS.register(new ConfigManager());
    ConfigManager.init(event.getSuggestedConfigurationFile());
    proxy.preInit(event);
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }
}
