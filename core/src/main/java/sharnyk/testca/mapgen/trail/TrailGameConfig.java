package sharnyk.testca.mapgen.trail;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TrailGameConfig {

  private int height;
  private int width;
  private int pathLength;
  private int maxChunkLength;

}
