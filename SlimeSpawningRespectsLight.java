package jakj.ssrl ;

import net.minecraft.entity.Entity ;
import net.minecraft.entity.monster.EntitySlime ;
import net.minecraft.util.math.BlockPos ;
import net.minecraft.world.World ;
import net.minecraftforge.common.config.Config ;
import net.minecraftforge.common.config.ConfigManager ;
import net.minecraftforge.event.entity.living.LivingSpawnEvent ;
import net.minecraftforge.fml.client.event.ConfigChangedEvent ;
import net.minecraftforge.fml.common.Mod ;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent ;

@Mod ( modid = "jakj.ssrl" , useMetadata = true )
@Mod.EventBusSubscriber
public class SlimeSpawningRespectsLight
{
    @Config ( modid = "jakj.ssrl" , name = "SlimeSpawningRespectsLight" )
    public static class Configuration
    {
        @Config.Name ( "Spawning Checks For Artificial Light" )
        public static boolean CheckLightBeforeSpawning = true ;
    }

    @SubscribeEvent
    public static void HandleConfigurationChangedEvent ( ConfigChangedEvent.OnConfigChangedEvent Event )
    {
        if ( Event . getModID ( ) . equals ( "jakj.ssrl" ) )
        {
            ConfigManager . sync ( "jakj.ssrl" , Config.Type . INSTANCE ) ;
        }
    }

    @SubscribeEvent
    public static void HandleCheckSpawnEvent ( LivingSpawnEvent.CheckSpawn Event )
    {
        if ( Configuration . CheckLightBeforeSpawning )
        {
            Entity Entity = Event . getEntity ( ) ;

            if ( Entity . getClass ( ) == EntitySlime . class )
            {
                World World = Event . getWorld ( ) ;

                BlockPos Position = new BlockPos ( Event . getX ( ) , Event . getY ( ) , Event . getZ ( ) ) ;

                int LightLevel = World . getLightFromNeighbors ( Position ) ;

                if ( LightLevel >= 8 )
                {
                    Event . setResult ( net.minecraftforge.fml.common.eventhandler.Event.Result . DENY ) ;
                }
            }
        }
    }
}