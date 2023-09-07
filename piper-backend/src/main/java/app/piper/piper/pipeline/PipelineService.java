package app.piper.piper.pipeline;

import app.piper.piper.common.PaginationRequest;
import app.piper.piper.common.PaginationResponse;
import app.piper.piper.util.PaginationMapper;
import app.piper.piper.util.SlugGenerator;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PipelineService {

    private final PipelineRepository pipelineRepository;

    private final PipelineMapper pipelineMapper;

    private final SlugGenerator slugGenerator;

    public PipelineResponse createPipeline(@NonNull PipelineRequest pipelineRequest) {
        try {
            // Map the PipelineRequest to a Pipeline entity
            Pipeline pipeline = pipelineMapper.pipelineRequestToPipeline(pipelineRequest);

            pipeline.setSlug(slugGenerator.generateSlug(pipeline.getName()));

            // Save the entity to the database
            Pipeline savedPipeline = pipelineRepository.save(pipeline);

            // Map the saved Pipeline entity to a PipelineResponse
            return pipelineMapper.pipelineToPipelineResponse(savedPipeline);
        }
        catch (DataIntegrityViolationException ex) {
            // Check the database error code or SQL state
            String errorCode = ex.getMostSpecificCause().getMessage();

            if (errorCode.contains(DuplicatePipelineNameException.DB_CONSTRAINT_NAME)) {
                throw new DuplicatePipelineNameException(ex);
            }
            else {
                // Handle other integrity violations or rethrow the exception
                throw ex;
            }
        }
    }

    public PaginationResponse<PipelineResponse> getPipelines(@NonNull PaginationRequest paginationRequest) {
        PageRequest pageRequest = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize(),
                Sort.by(Sort.Direction.fromString(paginationRequest.getSortDirection()),
                        paginationRequest.getSortBy()));

        Page<PipelineResponse> pipelineResponsePage = pipelineRepository.findAll(pageRequest)
                .map(pipelineMapper::pipelineToPipelineResponse);

        return PaginationMapper.map(pipelineResponsePage.getContent(), pipelineResponsePage);
    }

    public PipelineResponse findById(UUID pipelineId) {
        return pipelineMapper.pipelineToPipelineResponse(
                pipelineRepository.findById(pipelineId).orElseThrow(PipelineNotFoundException::new));
    }

}
