package dev.aziz.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectPerformerDto {

    @NotNull
    private Long oldPerformer;

    @NotNull
    private Long newPerformer;

    @NotNull
    private Long taskId;

}
