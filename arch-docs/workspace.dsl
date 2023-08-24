workspace {

    !adrs "adrs"

    model {
        user = person "User" "Controls personal finance. Reports spendings to a correct category to analyze them later"

        iris = softwareSystem "Iris" {
            description "Check balances in financial system against a state of a banking account. Reports mismatches to a task tracker"


            irisService = container "Iris Service" {
                description "Checks reporting mismatches periodically and on demand. Places reminders in task list."
                technology "Spring Boot"

                settlement = component "Settlement Module" {
                    description "Track settlement of spendings balance of accounting system against the current account balances in the Bank"
                    technology "Module"
                    tags "settlement"
                }

                subscription = component "Subscriptions Module" {
                    description "Tracks cost of subscriptions for different online services"
                    technology "Module"
                    tags "subscription"
                }

                homemoneyAdapter = component "Homemoney Adapter" {
                    description "Communicates with Homemoney system. Retrieves spending details for accounts"
                    technology "Driven Adapter"
                    tags "settlement"
                }

                monobankAdapter = component "Monobank Adapter" {
                    description "Communicates with Monobank. Retrieves current account balances"
                    technology "Driven Adapter"
                    tags "settlement" "subscription"
                }

                todoistAdapter = component "Todoist Adapter" {
                    description "Communicates with Todoist. Creates reminder tasks when settlement mismatch found"
                    technology "Driven Adapter"
                    tags "settlement"
                }

                scheduler = component "Scheduler" {
                    description "Runs tasks on scheduled intervals"
                    technology "Driver"
                    tags "settlement"
                }

                demoAdapter = component "Demo Adapter" {
                    description "Dummy implementation of accounting and bank. Allows running application in demo mode without connecting to real systems"
                    technology "Driven Adapter"
                    tags "settlement" "demo"
                }

                settlementRestAdapter = component "Settlement REST" {
                    description "Provides REST API to settlement module. Allows checking bank balances and accounting reports"
                    technology "Driver"
                    tags "settlement"
                }

                subscriptionRestAdapter = component "Subscription REST" {
                    description "Provides REST API to manage subscriptions details and see spendings"
                    technology "Driver"
                    tags "subscription"
                }

                subscriptionRestAdapter -> subscription "Reads cost data from"
                subscriptionRestAdapter -> subscription "Manages subscriptions using"

                settlementRestAdapter -> settlement "Reads settlement data from"

                settlement -> homemoneyAdapter "Retrieves spending details using"
                settlement -> monobankAdapter "Retrieves account balances using"
                settlement -> todoistAdapter "Notifies user about the mismatch using"
                settlement -> demoAdapter "Retrieves demo spendings and balances using"

                scheduler -> settlement "Triggers periodic settlement checks"

                subscription -> monobankAdapter "Retrieves currency exchange rates using"
            }

            irisFace = container "Iris UI" {
                description "Shows account balances. Allows triggering financial reporting discrepancy checks"
                technology "React"
            }

            irisDatabase = container "Iris Database" {
                description "Stores data locally. Reduces amount of requests to external systems"
                technology "MariaDB"
            }

            irisFace -> irisService "Retrieves data from" "REST/HTTPS"
            irisService -> irisDatabase "Reads and writes data to" "JDBC"
        }

        homemoney = softwareSystem "Homemoney" {
            description "Categorize spendings. Provides personal budget planning and analytics"
            tags "external"
        }

        monobank = softwareSystem "Monobank" {
            description "A personal banking. Provides account balances"
            tags "external"
        }

        todoist = softwareSystem "Todoist" {
            description "Personal task tracker. Reminds user about the necessity of checking the financial reporting"
            tags "external"
        }

        user -> iris "Checks discrepancies in reporting using"
        user -> irisFace "Checks account balances" "Browser"

        iris -> homemoney "Retrieves reported spendings from" "REST/HTTPS"
        iris -> monobank "Reads account balances from" "REST/HTTPS"
        iris -> todoist "Creates reminders to report spendings in" "REST/HTTPS"

        irisService -> homemoney "Retrieves reported spendings from" "REST/HTTPS"
        irisService -> monobank "Reads account balances from" "REST/HTTPS"
        irisService -> todoist "Creates reminders to report spendings in" "REST/HTTPS"

        deploymentEnvironment smallServer {
            deploymentNode raspberry {
                description "A machine where the system is hosted"
                technology "Raspberry PI"
                instances "1"

                reverseProxy = infrastructureNode "Reverse Proxy" {
                    description "Handles domain and API call routing. Delivers UI to users browser"
                    technology "Nginx"
                }

                dnsServer = infrastructureNode "DNS Server" {
                    description "Resolves domain names for human-friendly access to system"
                    technology "dnsMasq"
                }

                serviceInstance = containerInstance irisService {

                }

                uiInstance = containerInstance irisFace {
                }

                dbInstance = containerInstance irisDatabase {
                }
            }
            reverseProxy -> uiInstance "Delivers UI to browser"
            reverseProxy -> serviceInstance "Proxies calls from UI"
            reverseProxy -> dnsServer "Resolve IPs using"
        }
    }

    views {
        systemLandscape iris {
            include *
            autoLayout
        }

        systemContext iris {
            include *
            autoLayout
        }

        container iris {
            include *
            autoLayout
            title "Containers of iris system"
            description "Iris with external system context"
        }

        container iris {
            include *
            exclude homemoney monobank todoist user
            autoLayout
            title "Internal Iris containers"
            description "Internal containers of Iris system"
        }

        component irisService {
            include *
            autoLayout
            title "Iris service components overview"
            description "All components of the Iris service"
        }

        component irisService {
            include element.tag==subscription
            autoLayout
            title "Iris Service - subscription components"
            description "Components that builds the subscriptions functionaliy"
        }

        component irisService {
            include element.tag==settlement
            exclude element.tag==demo
            autoLayout
            title "Iris Service - settlement components"
            description "Components that builds the account settlement functionaliy"
        }

        deployment iris smallServer {
            include *
            autoLayout
            title "Small server deployment"
            description "System deployment on a single small computer"
        }
    }
}