package sharnyk.testca.mapgen.ships;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShipsGameConfig {

  private int height;
  private int width;
  private int pathLength;
//  private int maxChunkLength;

}
