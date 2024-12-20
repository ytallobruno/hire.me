type Environment = "development" | "production" | "test"

const environments: Record<Environment, { api: string }> = {
  development: {
    api: 'http://localhost:8080/v1'
  },
  production: {
    api: 'http://localhost:8080/v1'
  },
  test: {
    api: 'http://localhost:8080/v1'
  }
}

export const getApiUrl = (env: Environment): string => {
  return environments[env].api
}
