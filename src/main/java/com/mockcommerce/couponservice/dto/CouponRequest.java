package com.mockcommerce.couponservice.dto;

import java.math.BigDecimal;
import java.util.*;
public class CouponRequest {
    private String type;
    private CouponDetails details;

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CouponDetails getDetails() {
		return details;
	}

	public void setDetails(CouponDetails details) {
		this.details = details;
	}

    public static class CouponDetails {
        private BigDecimal threshold;
        private BigDecimal discount;
        private Integer product_id;
        private List<BuyProduct> buy_products;
        private List<GetProduct> get_products;
        private Integer repition_limit;

        public BigDecimal getThreshold() {
			return threshold;
		}

		public void setThreshold(BigDecimal threshold) {
			this.threshold = threshold;
		}

		public BigDecimal getDiscount() {
			return discount;
		}

		public void setDiscount(BigDecimal discount) {
			this.discount = discount;
		}

		public Integer getProduct_id() {
			return product_id;
		}

		public void setProduct_id(Integer product_id) {
			this.product_id = product_id;
		}

		public List<BuyProduct> getBuy_products() {
			return buy_products;
		}

		public void setBuy_products(List<BuyProduct> buy_products) {
			this.buy_products = buy_products;
		}

		public List<GetProduct> getGet_products() {
			return get_products;
		}

		public void setGet_products(List<GetProduct> get_products) {
			this.get_products = get_products;
		}

		public Integer getRepition_limit() {
			return repition_limit;
		}

		public void setRepition_limit(Integer repition_limit) {
			this.repition_limit = repition_limit;
		}

        public static class BuyProduct {
            private Integer product_id;
            private Integer quantity;
			public Integer getProduct_id() {
				return product_id;
			}
			public void setProduct_id(Integer product_id) {
				this.product_id = product_id;
			}
			public Integer getQuantity() {
				return quantity;
			}
			public void setQuantity(Integer quantity) {
				this.quantity = quantity;
			}
            
        }

        public static class GetProduct {
            private Integer product_id;
            private Integer quantity;
			public Integer getProduct_id() {
				return product_id;
			}
			public void setProduct_id(Integer product_id) {
				this.product_id = product_id;
			}
			public Integer getQuantity() {
				return quantity;
			}
			public void setQuantity(Integer quantity) {
				this.quantity = quantity;
			}
            
        }
    }
}
