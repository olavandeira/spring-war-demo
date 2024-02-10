Feature: Request price for a product

  Scenario Outline: Request price for a product for a brand at a specific moment in time
    When a price request is executed with <RequestTime> <ProductId> <BrandId>
    Then a price response is expected with <ProductId> <BrandId> <PriceId> <StartTime> <EndTime> <Price>
    Examples:
      | RequestTime           | ProductId | BrandId | StartTime             | EndTime               | Price       | PriceId |
      | "2020-06-14T10:00:00" | 35455     | 1       | "2020-06-14T00:00:00" | "2020-12-31T23:59:59" | "35.50 EUR" | 1       |
      | "2020-06-14T16:00:00" | 35455     | 1       | "2020-06-14T15:00:00" | "2020-06-14T18:30:00" | "25.45 EUR" | 2       |
      | "2020-06-14T21:00:00" | 35455     | 1       | "2020-06-14T00:00:00" | "2020-12-31T23:59:59" | "35.50 EUR" | 1       |
      | "2020-06-15T10:00:00" | 35455     | 1       | "2020-06-15T00:00:00" | "2020-06-15T11:00:00" | "30.50 EUR" | 3       |
      | "2020-06-16T21:00:00" | 35455     | 1       | "2020-06-15T16:00:00" | "2020-12-31T23:59:59" | "38.95 EUR" | 4       |


