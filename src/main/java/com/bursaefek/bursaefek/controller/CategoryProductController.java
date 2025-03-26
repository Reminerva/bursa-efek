package com.bursaefek.bursaefek.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bursaefek.bursaefek.constant.ApiBash;
import com.bursaefek.bursaefek.constant.ResponseExampleSwaggerBash;
import com.bursaefek.bursaefek.model.request.NewCategoryProductRequest;
import com.bursaefek.bursaefek.model.response.CategoryProductResponse;
import com.bursaefek.bursaefek.model.response.CommonResponse;
import com.bursaefek.bursaefek.service.CategoryProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Category Product API", description = "API untuk mengelola Category Product")
@RequiredArgsConstructor
@RequestMapping(ApiBash.CATEGORY_PRODUCT)
public class CategoryProductController {
    private final CategoryProductService categoryProductService;

    @PostMapping
    @Operation(
        summary = "Create category product", 
        description = "User dapat menambahkan kategori produk baru.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Category product created successfully",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.CREATE_CATEGORY_PRODUCT_SUCCESS)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.CREATE_CATEGORY_PRODUCT_FAILED)
                )
            )
        }
    )
    public ResponseEntity<CommonResponse<CategoryProductResponse>> createCategoryProduct(
            @RequestBody
            @Schema(example = "{ \"name\": \"Category Product\" }")
            NewCategoryProductRequest newCategoryProductRequest
    ) {

        try {
            CategoryProductResponse newProduct = categoryProductService.create(newCategoryProductRequest);
            CommonResponse<CategoryProductResponse> response = CommonResponse.<CategoryProductResponse>builder()
                .code(HttpStatus.CREATED.value())
                    .message(ApiBash.CREATE_CATEGORY_PRODUCT_SUCCESS)
                    .data(newProduct)
                    .build();
    
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            CommonResponse<CategoryProductResponse> response = CommonResponse.<CategoryProductResponse>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                    .message(ApiBash.CREATE_CATEGORY_PRODUCT_FAILED+ ": " + e.getMessage())
                    .data(null)
                    .build();
    
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    @Operation(
        summary = "Get all category product", 
        description = "User dapat melihat semua kategori produk.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Get all category product success",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.GET_ALL_CATEGORY_PRODUCT_SUCCESS)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.GET_ALL_CATEGORY_PRODUCT_SUCCESS)
                )
            )
        }
    )   
    public ResponseEntity<CommonResponse<List<CategoryProductResponse>>> getAllCategoryProduct() {
        return ResponseEntity.ok(CommonResponse.<List<CategoryProductResponse>>builder()
            .code(HttpStatus.OK.value())
            .message(ApiBash.GET_ALL_CATEGORY_PRODUCT_SUCCESS)
            .data(categoryProductService.getAll())
            .build());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get category product by id", 
        description = "User dapat melihat kategori produk berdasarkan id.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Get category product by id success",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.GET_CATEGORY_PRODUCT_SUCCESS)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.GET_CATEGORY_PRODUCT_FAILED)
                )
            )
        }
    )
    public ResponseEntity<CommonResponse<CategoryProductResponse>> getCategoryProductById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(CommonResponse.<CategoryProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message(ApiBash.GET_CATEGORY_PRODUCT_SUCCESS)
                .data(categoryProductService.getById(id))
                .build());
        } catch (Exception e) {
            return ResponseEntity.ok(CommonResponse.<CategoryProductResponse>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ApiBash.GET_CATEGORY_PRODUCT_FAILED+ ": " + e.getMessage())
                .data(null)
                .build());
        } 
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update category product", 
        description = "User dapat mengupdate kategori produk.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Update category product success",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.UPDATE_CATEGORY_PRODUCT_SUCCESS)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = ResponseExampleSwaggerBash.UPDATE_CATEGORY_PRODUCT_FAILED)
                )
            )
        }
    )
    public ResponseEntity<CommonResponse<CategoryProductResponse>> updateCategoryProduct(
            @RequestBody
            @Schema(example = "{ \"name\": \"Category Product\" }")
            NewCategoryProductRequest newCategoryProductRequest,
            @PathVariable String id
    ) {
        System.out.println(newCategoryProductRequest.getName());
        try {
            return ResponseEntity.ok(CommonResponse.<CategoryProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message(ApiBash.UPDATE_CATEGORY_PRODUCT_SUCCESS)
                .data(categoryProductService.update(newCategoryProductRequest, id))
                .build());
        } catch (Exception e) {
            return ResponseEntity.ok(CommonResponse.<CategoryProductResponse>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ApiBash.UPDATE_CATEGORY_PRODUCT_FAILED+ ": " + e.getMessage())
                .data(null)
                .build());
        }
    }
}
