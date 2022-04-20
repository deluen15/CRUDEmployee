terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "3.2.0"
    }
  }
}
provider "azurerm" {
  features {}
}
variable "imagebuild" {
  type        = string
  description = "Latest Image Build"
}

resource "azurerm_resource_group" "aks-IaC-test" {
  location = "West Europe"
  name     = "aks-IaC-test"
}
resource "azurerm_container_group" "tfcg-test" {
  location            = azurerm_resource_group.aks-IaC-test.location
  name                = "employer"
  os_type             = "Linux"
  resource_group_name = azurerm_resource_group.aks-IaC-test.name
  ip_address_type     = "Public"
  dns_name_label      = "employer"
  container {
    cpu    = "1"
    image  = "endritzeqo/employer_backend:${var.imagebuild}"
    memory = "1"
    name   = "employer"
    ports {
      port     = 8080
      protocol = "TCP"
    }
  }
}
