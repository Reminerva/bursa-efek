package com.bursaefek.bursaefek.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bursaefek.bursaefek.constant.ApiBash;
import com.bursaefek.bursaefek.constant.ResponseExampleSwaggerBash;
import com.bursaefek.bursaefek.model.request.NewProductRequest;
import com.bursaefek.bursaefek.model.response.CommonResponse;
import com.bursaefek.bursaefek.model.response.ProductResponse;
import com.bursaefek.bursaefek.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Product API", description = "API untuk mengelola Product")
@RequiredArgsConstructor
@RequestMapping(ApiBash.PRODUCT)
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(
        summary = "Create product", 
        description = "User dapat menambahkan produk baru.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Product created successfully",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.CREATE_PRODUCT_SUCCESS)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.CREATE_PRODUCT_FAILED)
                )
            )
        }
    )
    public ResponseEntity<CommonResponse<ProductResponse>> createProduct(
            @RequestBody
            @Schema(example = "{ \"productName\": \"Product\", \"price\": 10000, \"stock\": 10, \"categoryProductId\": \"a1b2c3d4-e5f6-7890-gh12-ijkl34567890\" }")
            @Valid
            NewProductRequest newProductRequest
    ) {

        try {
            ProductResponse newProduct = productService.create(newProductRequest);
            CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .code(HttpStatus.CREATED.value())
                    .message(ApiBash.CREATE_PRODUCT_SUCCESS)
                    .data(newProduct)
                    .build();
    
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                    .message(ApiBash.CREATE_PRODUCT_FAILED+ ": " + e.getMessage())
                    .data(null)
                    .build();
    
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    @Operation(
        summary = "Get all product", 
        description = "User dapat melihat semua produk.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Get all product success",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.GET_ALL_PRODUCT_SUCCESS)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.GET_ALL_PRODUCT_SUCCESS)
                )
            )
        }
    )    
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getAllProduct() {
        return ResponseEntity.ok(CommonResponse.<List<ProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(ApiBash.GET_ALL_PRODUCT_SUCCESS)
                .data(productService.getAll())
                .build()
                );
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get product by id", 
        description = "User dapat melihat produk berdasarkan id.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Get product by id success",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.GET_PRODUCT_SUCCESS)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.GET_PRODUCT_FAILED)
                )
            )
        }
    )
    public ResponseEntity<CommonResponse<ProductResponse>> getProductById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(CommonResponse.<ProductResponse>builder()
                    .code(HttpStatus.OK.value())
                    .message(ApiBash.GET_PRODUCT_SUCCESS)
                    .data(productService.getById(id))
                    .build()
                    );
        } catch (Exception e) {
            return ResponseEntity.ok(CommonResponse.<ProductResponse>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ApiBash.GET_PRODUCT_FAILED+ ": " + e.getMessage())
                    .data(null)
                    .build()
                    );
        }
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update product", 
        description = "User dapat mengupdate produk.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Update product success",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.UPDATE_PRODUCT_SUCCESS)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.UPDATE_PRODUCT_FAILED)
                )
            )
        }
    )
    public ResponseEntity<CommonResponse<ProductResponse>> updateProduct(@RequestBody NewProductRequest newProductRequest, @PathVariable String id) {
        try {
            return ResponseEntity.ok(CommonResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message(ApiBash.UPDATE_PRODUCT_SUCCESS)
                .data(productService.update(newProductRequest, id))
                .build()
                );
        } catch (Exception e) {
            return ResponseEntity.ok(CommonResponse.<ProductResponse>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ApiBash.UPDATE_PRODUCT_FAILED+ ": " + e.getMessage())
                .data(null)
                .build()
                );
        }
    }

    @DeleteMapping("hard-delete/{id}")
    @Operation(
        summary = "Hard delete product", 
        description = "User dapat menghapus produk secara permanen.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Hard delete product success",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.HARD_DELETE_PRODUCT_SUCCESS)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.HARD_DELETE_PRODUCT_FAILED)
                )
            )
        }
    )
    public ResponseEntity<CommonResponse<Void>> hardDeleteProduct(@PathVariable String id) {
        try {
            productService.hardDelete(id);
            return ResponseEntity.ok(CommonResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message(ApiBash.HARD_DELETE_PRODUCT_SUCCESS)
                .data(null)
                .build()
                );
        } catch (Exception e) {
            return ResponseEntity.ok(CommonResponse.<Void>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ApiBash.HARD_DELETE_PRODUCT_FAILED+ ": " + e.getMessage())
                .data(null)
                .build()
                );
        }
    }

    @DeleteMapping("soft-delete/{id}")
    @Operation(
        summary = "Soft delete product", 
        description = "User dapat menghapus produk dengan mengubah status isPriceActive.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Soft delete product success",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.SOFT_DELETE_PRODUCT_SUCCESS)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.SOFT_DELETE_PRODUCT_FAILED)
                )
            )
        }
    )
    public ResponseEntity<CommonResponse<Void>> softDeleteProduct(@PathVariable String id) {
        try {
            productService.softDelete(id);
            return ResponseEntity.ok(CommonResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message(ApiBash.SOFT_DELETE_PRODUCT_SUCCESS)
                .data(null)
                .build()
                );
        } catch (Exception e) {
            return ResponseEntity.ok(CommonResponse.<Void>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ApiBash.SOFT_DELETE_PRODUCT_FAILED+ ": " + e.getMessage())
                .data(null)
                .build()
                );
        }
    }
}
