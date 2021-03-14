# Бессерверное приложение на примере AWS Lambda с использованием Kotless

## Запуск приложения

### Установка клиентов
```bash
pip3 install localstack
brew install terraform
localstack start
```

### AWS
* Routing subdomains with AWS and Route 53
https://superuser.com/questions/1179808/routing-subdomains-with-aws-and-route-53
You need to delegate the subdomain (here.example.com) only to Route 53.

* Error: No certificate for domain "foo.com" found in this region
https://githubmemory.com/repo/JetBrains/kotless/issues?cursor=Y3Vyc29yOnYyOpK5MjAyMC0xMS0wN1QyMzoxMTowMCswODowMM4sAPL4&pagination=next&page=3
ACM certificate needs to be in *us-east-1*

* Why am I getting an HTTP 307 Temporary Redirect response
https://aws.amazon.com/ru/premiumsupport/knowledge-center/s3-http-307-response/
After you create an Amazon S3 bucket, it can take up to 24 hours for the bucket name to propagate across all AWS Regions.

### Очистка DNS-кэша после деплоя
Для очистки DNS-кэша и доступа к доменному имени, после деплоя рекомендуется выполнить:

#### macOS
сlear DNS Cache
```bash
sudo dscacheutil -flushcache
sudo killall -HUP mDNSResponder
```
querying DNS
```bash
dscacheutil -q host -a name example.com
```

#### Ubuntu
```bash
sudo systemd-resolve --statistics
```

```bash
sudo systemd-resolve --flush-caches
sudo systemd-resolve --statistics
```
## Материалы
* From Zero to Lambda with Kotless
https://hadihariri.com/2020/05/12/from-zero-to-lamda-with-kotless/

* Example
https://github.com/JetBrains/kotless/tree/master/examples/kotless/shortener
